<template>
  <v-container v-if="!$fetchState.pending" fluid>

    <v-card flat>
      <v-card-title>Basic configuration</v-card-title>

      <validation-observer ref="validatorBasicConfiguration" tag="form" @submit.prevent="submit">
        <v-card-text>
          <validation-provider #default="{errors}" name="name" rules="required">
            <v-text-field v-model="content.name" :error-messages="errors[0]" dense label="Name" name="name" outlined></v-text-field>
          </validation-provider>
          <v-card-actions>
            <v-btn color="success" small type="submit">Save data</v-btn>
          </v-card-actions>
        </v-card-text>
      </validation-observer>
    </v-card>

    <v-divider class="my-3"></v-divider>

    <template v-if="mode === 'edit'">
      <v-card flat>
        <v-card-title>Fields</v-card-title>

        <v-card-text>
          <v-simple-table dense>
            <thead>
              <tr>
                <th></th>
                <th></th>
                <th></th>
                <th>Displayed</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(field, fieldIndex) in content.fields" :key="fieldIndex">
                <td class="text-no-wrap" style="width: 1rem">
                  <v-icon small>{{ getIcon(field.type) }}</v-icon>
                </td>
                <td>
                  {{ field.name }} <span v-if="!field.nullable" class="ml-1 red--text font-weight-bold">*</span>
                </td>
                <td>
                  <span>{{ field.type.code }}</span>
                  <span v-if="field.type.code === 'RELATION'" class="ml-1 text-caption font-italic">with {{ field.params.relationContent.name }}</span>
                </td>
                <td>
                  <v-radio-group v-model="content.displayedField" class="pa-0" dense @change="updateContent">
                    <v-radio v-if="isDisplayedFieldValidType(field)" :ripple="false" :value="field.fieldName" style="height: 0"/>
                  </v-radio-group>
                </td>
                <td class="text-no-wrap" style="width: 1rem">
                  <template v-if="!field.primaryKey">
                    <v-btn color="blue" fab text x-small @click="editField(field)">
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                    <v-btn color="red" fab text x-small @click="deleteField(field)">
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </template>
                </td>
              </tr>
            </tbody>
          </v-simple-table>

          <v-btn class="mt-5" color="success" small type="button" @click="addField">Add new field</v-btn>
        </v-card-text>
      </v-card>
    </template>

    <v-dialog v-model="dialogs.dialogEditField" fullscreen>
      <v-card v-if="fieldEdit != null" tile>
        <validation-observer ref="validatorDialogEditField" tag="form" @submit.prevent="submitField">
          <v-toolbar color="primary" dark dense flat>
            <v-btn dark icon @click="dialogs.dialogEditField = false">
              <v-icon>mdi-close</v-icon>
            </v-btn>
            <v-toolbar-title v-text="fieldEdit._id == null ? 'Add new field' : `Edit field ${ content.name } / ${ fieldEdit.name }`"></v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn text type="submit">Save</v-btn>
            </v-toolbar-items>
          </v-toolbar>

          <v-card-text>
            <v-row>
              <v-col cols="12" md="6">
                <validation-provider #default="{errors}" name="name" rules="required">
                  <v-text-field v-model="fieldEdit.name" :error-messages="errors[0]" dense label="Name" outlined></v-text-field>
                </validation-provider>
              </v-col>
              <v-col cols="12" md="6">
                <validation-provider #default="{errors}" name="type" rules="required">
                  <v-select v-model="fieldEdit.type" :error-messages="errors[0]" :items="fieldTypes" dense item-text="name" label="Type" outlined return-object>
                    <template #item="{item}">
                      <v-icon>{{ getIcon(item) }}</v-icon>
                      <span class="ml-4">{{ item.name }}</span>
                    </template>
                  </v-select>
                </validation-provider>
              </v-col>
            </v-row>

            <div v-if="fieldEdit.type">
              <component :is="paramsComponent" v-model="fieldEdit" :mode="modeField"></component>
            </div>

            <v-row>
              <v-col>
                <v-checkbox v-model="fieldEdit.nullable" label="Nullable"></v-checkbox>
              </v-col>
            </v-row>
          </v-card-text>
        </validation-observer>
      </v-card>
    </v-dialog>

  </v-container>
</template>

<script lang="ts">
import { Action, Component, Ref, State, Vue } from 'nuxt-property-decorator'
import { TYPE } from 'vue-toastification/src/ts/constants'

declare type EditMode = 'add' | 'edit'

@Component
export default class PageContent extends Vue {
  @Ref('validatorBasicConfiguration') readonly validatorBasicConfiguration!: any
  @Ref('validatorDialogEditField') readonly validatorDialogEditField!: any

  @State(state => state.list.fieldtypes) fieldTypes
  @Action('list/getFieldTypes') getFieldTypes
  @Action('getContents') getContents

  content = null
  fieldEdit = null

  dialogs = {
    dialogEditField: false,
  }

  get mode (): EditMode {
    return this.$route.params.idContent != null ? 'edit' : 'add'
  }

  get modeField (): EditMode {
    return this.fieldEdit._id != null ? 'edit' : 'add'
  }

  get paramsComponent () {
    if (this.fieldEdit.type != null) {
      const type = typeof this.fieldEdit.type === 'string' ? this.fieldEdit.type : this.fieldEdit.type.code
      return 'app-fields-params-' + type.toLowerCase()
    }
    return null
  }

  async fetch () {
    if (this.$route.params.idContent != null) {
      this.content = await this.$api.content.getById(this.$route.params.idContent)
    } else {
      this.content = { name: '' }
    }
  }

  async addField () {
    this.fieldEdit = { nullable: true }
    await this.getFieldTypes()
    this.dialogs.dialogEditField = true
  }

  async deleteField (field) {
    const confirm = await this.$confirm.open('Confirmation', 'Are you sure ?', { color: 'warning' })
    if (confirm) {
      await this.$api.content_fields.remove(field._id)
      this.content.fields = this.content.fields.filter(contentField => field.fieldName !== contentField.fieldName)
      this.$toast('Field is now deleted', { type: TYPE.SUCCESS })
    }
  }

  async editField (field) {
    this.fieldEdit = { ...field }
    await this.getFieldTypes()
    this.dialogs.dialogEditField = true
  }

  getIcon (type) {
    switch (type?.code) {
      case 'STRING':
        return 'mdi-format-color-text'
      case 'TEXT':
        return 'mdi-text-box-outline'
      case 'RICHTEXT':
        return 'mdi-signature-text'
      case 'UID':
        return 'mdi-identifier'
      case 'NUMBER':
        return 'mdi-numeric'
      case 'DATE':
        return 'mdi-calendar'
      case 'TIME':
        return 'mdi-clock-outline'
      case 'DATETIME':
        return 'mdi-calendar-clock'
      case 'RELATION':
        return 'mdi-relation-many-to-many'
      default:
        break
    }
  }

  isDisplayedFieldValidType (field) {
    if (field.type == null) {
      return false
    }
    return [ 'STRING', 'TEXT', 'UID', 'NUMBER', 'DATE', 'TIME', 'DATETIME' ].indexOf(field.type.code) > -1
  }

  async submit () {
    const valid = this.validatorBasicConfiguration.validate()
    if (valid) {
      if (this.mode === 'edit') {
        await this.updateContent()
        this.$toast("Content updated", { type: TYPE.SUCCESS })
      } else {
        const content = await this.$api.content.create(this.content)
        if (content !== '') {
          await this.$router.push({ name: 'content', params: { idContent: content._id } })
        }
        this.$toast('Content is now created', { type: TYPE.SUCCESS })
      }
      await this.getContents()
    }
  }

  async submitField () {
    const valid = await this.validatorDialogEditField.validate()

    if (valid) {
      let field
      this.fieldEdit.type = this.fieldEdit.type.code
      this.fieldEdit.contentId = this.content._id

      if (this.modeField === 'edit') {
        field = await this.$api.content_fields.update(this.fieldEdit._id, this.fieldEdit)
        this.$set(this.content.fields, this.content.fields.findIndex(field => field.fieldName === this.fieldEdit.fieldName), field)

        this.$toast("Field updated", { type: TYPE.SUCCESS })
      } else {
        field = await this.$api.content_fields.create(this.fieldEdit)
        if (field !== '') {
          this.content.fields.push(field)
        }

        this.$toast("Field created", { type: TYPE.SUCCESS })
      }
      this.fieldEdit = null
      this.dialogs.dialogEditField = false
    }
  }

  async updateContent () {
    this.content = await this.$api.content.update(this.content._id, this.content)
  }
}
</script>

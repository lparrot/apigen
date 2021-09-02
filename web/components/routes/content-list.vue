<template>
  <v-container v-if="!$fetchState.pending" fluid>

    <div>
      <v-btn :to="{name: 'content-item', params: {idContent: content._id}}" color="primary" small>Add new item</v-btn>
      <v-btn :to="{name: 'content', params: {idContent: content._id}}" color="primary" small>Edit content structure</v-btn>
      <v-btn color="error" small @click="deleteContent">Delete content {{ content.name }}</v-btn>
    </div>

    <div class="d-flex justify-space-between my-2">
      <div>

      </div>
      <div>
        <v-menu v-model="menuOptions" :close-on-content-click="false" :nudge-width="200" offset-y>

          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" icon text v-bind="attrs" v-on="on">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>

          <v-card>
            <v-card-text>
              <div class="subtitle-1">Shown fields</div>

              <template v-for="(field, fieldIndex) in fields">
                <div v-if="field.value !== 'actions'" :key="fieldIndex">
                  <v-checkbox :label="field.name" :value="field.hide" dense hide-details>
                    <template #label>
                      <div class="text-caption">{{ field.name }}</div>
                    </template>
                  </v-checkbox>
                </div>
              </template>
            </v-card-text>
          </v-card>

        </v-menu>
      </div>
    </div>

    <v-data-table :headers="fields" :items="items" :options.sync="options" :server-items-length="totalItems">
      <template v-for="(field, fieldIndex) in fields" v-slot:[getCellSlot(field.value)]="{value}">
        <span v-if="field.type.code === 'RELATION' && value != null">{{ $utils.get(value, field.params.relationContent.displayedField, value._id) }}</span>
        <span v-else>{{ value }}</span>
      </template>

      <template #item.actions="{item}">
        <v-btn :to="{name: 'content-item', params: {idContent: content._id, idItem: item._id}}" color="info" fab x-small>
          <v-icon small>mdi-pencil</v-icon>
        </v-btn>
        <v-btn color="error" fab x-small @click="removeItem(item)">
          <v-icon small>mdi-delete</v-icon>
        </v-btn>
      </template>
    </v-data-table>
  </v-container>
</template>

<script lang="ts">
import { Action, Component, Vue, Watch } from 'nuxt-property-decorator'
import { DataOptions } from 'vuetify'
import { TYPE } from 'vue-toastification/src/ts/constants'

@Component
export default class PageContentList extends Vue {

  @Action('getContents') getContents

  content = null
  menuOptions = false
  items = []
  totalItems = null
  options: Partial<DataOptions> = {}

  get fields () {
    let fields = this.content.fields
      .map(field => {
        return { ...field, value: field.fieldName, text: field.name }
      })
    fields.push({ value: 'actions', sortable: false, cellClass: 'text-no-wrap', width: '1rem' })
    return fields
  }

  async fetch () {
    this.content = await this.$api.content.getById(this.$route.params.idContent)
  }

  async deleteContent () {
    const confirm = await this.$confirm.open('Confirmation', 'Are you sure ?', { color: 'warning' })
    if (confirm) {
      await this.$api.content.remove(this.content._id)
      this.$toast('Content is now deleted', { type: TYPE.SUCCESS })
      await this.$router.push({ name: 'content' })
      await this.getContents()
    }
  }

  getCellSlot (key) {
    return `item.${ key }`
  }

  async removeItem (item) {
    const confirm = await this.$confirm.open('Confirmation', 'Are you sure ?', { color: 'warning' })
    if (confirm) {
      await this.$api.item.remove(this.content.slug, item._id)
      this.$toast('Item is now deleted', { type: TYPE.SUCCESS })
      await this.search()
    }
  }

  async search () {
    const res = await this.$api.item.getAll(this.content.slug, this.$utils.convertPagination(this.options))
    this.items = res.content
    this.totalItems = res.totalElements
  }

  @Watch('options', { deep: true })
  async onChangeOptions (options: DataOptions) {
    await this.search()
  }
}
</script>

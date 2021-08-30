<template>
  <v-container v-if="!$fetchState.pending" fluid>
    <h3 class="mb-4">{{ mode === 'add' ? `Adding new ${ content.name }` : `Edit ${ content.name }` }}</h3>

    <validation-observer ref="validator" tag="form" @submit.prevent="submit">
      <div v-for="(field, fieldIndex) in fields" :key="fieldIndex">
        <validation-provider #default="{errors}" :name="field.fieldName" :rules="{
            required: !field.nullable,
            numeric: field.type.code === 'NUMBER'
          }">
          <template v-if="field.type.code === 'STRING'">
            <v-text-field v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined></v-text-field>
          </template>

          <template v-if="field.type.code === 'NUMBER'">
            <v-text-field v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined type="number"></v-text-field>
          </template>

          <template v-if="field.type.code === 'TEXT'">
            <v-textarea v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined></v-textarea>
          </template>

          <template v-if="field.type.code === 'RICHTEXT'">
            <v-textarea v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined></v-textarea>
          </template>

          <template v-if="field.type.code === 'UID'">
            <v-text-field v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined>
              <template #append-outer>
                <v-btn icon small @click="$set(item, field.fieldName, $utils.generateUUID())">
                  <v-icon>mdi-sync</v-icon>
                </v-btn>
              </template>
            </v-text-field>
          </template>

          <template v-if="field.type.code === 'DATE'">
            <v-text-field v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined type="date"></v-text-field>
          </template>

          <template v-if="field.type.code === 'TIME'">
            <v-text-field v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined type="time"></v-text-field>
          </template>

          <template v-if="field.type.code === 'DATETIME'">
            <v-text-field v-model="item[field.fieldName]" :error-messages="errors[0]" :label="field.name" dense outlined type="datetime-local"></v-text-field>
          </template>

          <template v-if="field.type.code === 'RELATION'">
            <v-select v-model="item[field.fieldName]" :error-messages="errors[0]" :items="relations[field.relationContent.slug]" :label="field.name" dense outlined></v-select>
          </template>
        </validation-provider>
      </div>

      <v-btn class="mt-2" color="success" type="submit">{{ mode === 'add' ? 'Create' : 'Update' }}</v-btn>
    </validation-observer>
  </v-container>
</template>

<script lang="ts">
import { Component, Ref, Vue } from 'nuxt-property-decorator'

declare type EditMode = 'add' | 'edit'

@Component
export default class PageContentItem extends Vue {
  @Ref('validator') readonly validator!: any

  content: any = {}
  item: any = {}
  relations = {}

  get fields () {
    return this.content.fields
      .filter(field => !field.primaryKey)
  }

  get mode (): EditMode {
    return this.$route.params.idItem != null ? 'edit' : 'add'
  }

  async fetch () {
    this.content = await this.$api.content.getById(this.$route.params.idContent)
    const relationFields = this.content.fields.filter(field => field.type.code === 'RELATION')
    if (this.content == null) {
      return this.$nuxt.error({ statusCode: 404, path: '404', message: 'This page could not be found' })
    }
    if (this.mode === 'edit') {
      this.item = await this.$api.item.getById(this.content.slug, this.$route.params.idItem)

      relationFields.forEach(relationField => {
        if (this.item[relationField.fieldName] != null) {
          this.item[relationField.fieldName] = this.item[relationField.fieldName]._id
        }
      })

      if (this.item == null) {
        return this.$nuxt.error({ statusCode: 404, path: '404', message: 'This page could not be found' })
      }
    }

    for await (let field of relationFields) {
      this.relations[field.relationContent.slug] = await this.$api.relation.getAllData(field.relationContent._id);
    }

    console.log(this.relations)
  }

  async submit () {
    const valid = await this.validator.validate()
    if (valid) {
      switch (this.mode) {
        case 'add':
          await this.$api.item.create(this.content.slug, this.item)
          break
        case 'edit':
          await this.$api.item.update(this.content.slug, this.item._id, this.item)
          break
        default:
          break
      }
      await this.$router.push({ name: 'content-list', params: { idContent: this.content._id } })
    }
  }
}
</script>

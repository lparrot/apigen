<template>
  <v-container v-if="!$fetchState.pending" fluid>

    <div>
      <v-btn :to="{name: 'content-item', params: {idContent: content._id}}" color="primary" small>Add new item</v-btn>
      <v-btn :to="{name: 'content', params: {idContent: content._id}}" color="primary" small>Edit content structure</v-btn>
      <v-btn color="error" small @click="deleteContent">Delete content {{ content.name }}</v-btn>
    </div>

    <v-data-table :headers="fields" :items="items" :options.sync="options" :server-items-length="totalItems">
      <template v-for="(field, fieldIndex) in fields" v-slot:[getCellSlot(field.value)]="{value}">
        <span v-if="field.type.code === 'RELATION'">{{ $utils.get(value, field.params.relationContent.displayedField, value._id) }}</span>
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

@Component
export default class PageContentList extends Vue {

  @Action('getContents') getContents

  content = null
  items = null
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
    await this.search()
  }

  async deleteContent () {
    const confirm = await this.$confirm.open('Confirmation', 'Are you sure ?', { color: 'warning' })
    if (confirm) {
      await this.$api.content.remove(this.content._id)
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

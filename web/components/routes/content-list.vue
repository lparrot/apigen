<template>
  <v-container v-if="!$fetchState.pending" fluid>

    <div>
      <v-btn :to="{name: 'content-item', params: {idContent: content._id}}" color="primary" small>Add new item</v-btn>
      <v-btn :to="{name: 'content', params: {idContent: content._id}}" color="primary" small>Edit content structure</v-btn>
      <v-btn color="error" small @click="deleteContent">Delete content {{ content.name }}</v-btn>
    </div>

    <v-data-table :headers="fields" :items="items" :options.sync="pagination">
      <template v-for="(field, fieldIndex) in fields" v-slot:[getCellSlot(field.value)]="{item}">
        <!--        <span v-if="field.type.code === 'RELATION' && field.relationContent != null">{{ $utils.get(item, [field.value, field.relationContent.displayedField].join('.'), item._id) }}</span>-->
        <span>{{ item[field.value] }}</span>
      </template>

      <template #item.actions="{item}">
        <v-btn :to="{name: 'content-item', params: {idContent: content._id, idItem: item._id}}" color="info" fab x-small>
          <v-icon small>mdi-pencil</v-icon>
        </v-btn>
        <v-btn color="error" fab x-small>
          <v-icon small>mdi-delete</v-icon>
        </v-btn>
      </template>
    </v-data-table>
  </v-container>
</template>

<script lang="ts">
import { Action, Component, Vue } from 'nuxt-property-decorator'

@Component
export default class PageContentList extends Vue {

  @Action('getContents') getContents

  content = null
  items = null
  pagination: any = { page: 1, itemsPerPage: 5, total: 0 }

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
    await this.$api.content.remove(this.content._id)
    await this.$router.push({ name: 'content' })
    await this.getContents()
  }

  getCellSlot (key) {
    return `item.${ key }`
  }

  async search () {
    const res = await this.$api.item.getAll(this.content.slug, { size: this.pagination.itemsPerPage, page: this.pagination.page })
    this.items = res.content
    this.pagination.total = res.numberOfElements
  }
}
</script>

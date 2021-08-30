<template>
  <v-container fluid>
    <v-data-table :headers="headers" :items="events" dense>
      <template #item.timestamp="{item}">
        <span class="text-no-wrap">{{ new Date(item.timestamp).toLocaleDateString() }} {{ new Date(item.timestamp).toLocaleTimeString() }}</span>
      </template>
      <template #item.type="{item}">
        <span>{{ item.type }}</span>
      </template>
      <template #item.data="{item}">
        <div v-for="(data, dataName, dataIndex) in item.data" :key="dataIndex">
          {{ dataName }} : {{ data }}
        </div>
      </template>
    </v-data-table>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'
import { Context } from '@nuxt/types'

@Component
export default class PageAdminEvents extends Vue {
  events = null
  headers = null

  async asyncData (ctx: Context) {
    const res = await ctx.$api.admin.getAuditEvents()

    return {
      events: res.events,
      headers: [
        { text: 'Timestamp', value: 'timestamp' },
        { text: 'Type', value: 'type' },
        { text: 'Data', value: 'data' },
      ],
    }
  }
}
</script>

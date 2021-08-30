<template>
  <v-container fluid>
    <v-data-table :headers="headers" :items="traces" dense>
      <template #item.timestamp="{ item }">
        <span class="text-no-wrap">{{ new Date(item.timestamp).toLocaleDateString() }} {{ new Date(item.timestamp).toLocaleTimeString() }}</span>
      </template>

      <template #item.method="{ item }">
        <span :class="getMethodColor(item.request.method)">{{ item.request.method }}</span>
      </template>

      <template #item.status="{ item }">
        <v-chip :color="getStatusColor(item.response.status)" x-small>{{ item.response.status }}</v-chip>
      </template>

      <template #item.time="{ item }">
        <span>{{ item.timeTaken }} ms</span>
      </template>

      <template #item.path="{ item }">
        <span class="grey--text">{{ item.request.uri }}</span>
      </template>

    </v-data-table>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'
import { Context } from '@nuxt/types'

@Component
export default class PageAdminTraces extends Vue {
  traces = null
  headers = null

  async asyncData (ctx: Context) {
    const res = await ctx.$api.admin.getHttpTraces()
    return {
      traces: res.traces,
      headers: [
        { text: 'Timestamp', value: 'timestamp' },
        { text: 'Method', value: 'method' },
        { text: 'Status', value: 'status' },
        { text: 'Time', value: 'time' },
        { text: 'Path', value: 'path' },
      ],
    }
  }

  getMethodColor (method) {
    switch (method) {
      case 'GET':
        return 'info--text'
      case 'PUT':
      case 'POST':
        return 'success--text'
      case 'DELETE':
        return 'error--text'
      default:
        return 'grey--text'
    }
  }

  getStatusColor (status) {
    const statusCode: string = String(status)
    if (statusCode.startsWith('1') || statusCode.startsWith('2')) {
      return 'success'
    } else if (statusCode.startsWith('3')) {
      return 'warning'
    } else {
      return 'error'
    }
  }
}
</script>

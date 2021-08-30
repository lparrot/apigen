<template>
  <v-container fluid>
    <v-btn color="success" @click="openFile">Download file</v-btn>

    <v-divider class="my-4"></v-divider>

    <v-sheet class="overflow-auto" height="40rem">
      <div class="grey--text text-pre text-caption">{{ logs }}</div>
      <div ref="divBottom"></div>
    </v-sheet>
  </v-container>
</template>

<script lang="ts">
import { Component, Ref, Vue } from 'nuxt-property-decorator'
import { Context } from '@nuxt/types'

@Component
export default class PageAdminLogs extends Vue {
  @Ref('divBottom') divBottom

  logs = null

  async asyncData (ctx: Context) {
    const logs = await ctx.$api.admin.getLogs()
    return {
      logs,
    }
  }

  async mounted () {
    this.divBottom.scrollIntoView()
  }

  async openFile () {
    window.open('/api/actuator/logfile')
  }
}
</script>

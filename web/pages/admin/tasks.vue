<template>
  <v-container fluid>
    {{ tasks }}
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'

@Component
export default class PageAdminTasks extends Vue {
  tasks = null

  async fetch () {
    this.tasks = await this.$api.admin.getScheduledTasks()
  }
}
</script>

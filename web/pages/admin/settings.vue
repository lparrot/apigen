<template>
  <v-container fluid>
    <v-btn color="success" @click="restartApplication">Restart application</v-btn>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'

@Component
export default class PageAdminSettings extends Vue {
  async restartApplication () {
    await this.$api.admin.restartApplication()
  }
}
</script>

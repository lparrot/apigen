<template>
  <v-dialog v-model="value" :max-width="options.width" :style="{ zIndex: options.zIndex }" @keydown.esc="cancel">
    <v-card>
      <v-toolbar :color="options.color" dark dense flat>
        <v-toolbar-title class="white--text">{{ title }}</v-toolbar-title>
      </v-toolbar>
      <v-card-text v-show="!!message" class="pa-4">{{ message }}</v-card-text>
      <v-card-actions class="pt-0">
        <v-spacer></v-spacer>
        <v-btn color="grey" text @click="cancel">{{ options.cancelLabel }}</v-btn>
        <v-btn color="primary darken-1" text @click="agree">{{ options.okLabel }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'

@Component
export default class ConfirmDialog extends Vue {
  value = false
  resolve = null
  reject = null
  message = null
  title = null

  options: ConfirmDialogOption = {
    color: 'primary',
    width: 290,
    zIndex: 200,
    okLabel: 'OK',
    cancelLabel: 'Cancel',
  }

  open (title, message, options: ConfirmDialogOption): Promise<boolean> {
    this.value = true
    this.title = title
    this.message = message
    this.options = Object.assign(this.options, options)
    return new Promise((resolve, reject) => {
      this.resolve = resolve
      this.reject = reject
    })
  }

  agree () {
    this.resolve(true)
    this.value = false
  }

  cancel () {
    this.resolve(false)
    this.value = false
  }
}
</script>

<template>
  <v-row>
    <v-col cols="12" md="6">
      <v-checkbox v-model="field.params.password" label="Password"></v-checkbox>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, mixins } from 'nuxt-property-decorator'
import { FieldParamsMixin } from '~/mixins/field-params.mixin'

@Component
export default class StringParam extends mixins(FieldParamsMixin) {
}
</script>

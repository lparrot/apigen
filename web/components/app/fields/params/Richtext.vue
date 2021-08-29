<template>
  <v-row>

  </v-row>
</template>

<script lang="ts">
import { Component, mixins } from 'nuxt-property-decorator'
import { FieldParamsMixin } from '~/mixins/field-params.mixin'

@Component
export default class RichtextParam extends mixins(FieldParamsMixin) {
}
</script>

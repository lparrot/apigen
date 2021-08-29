<template>
  <v-row v-if="!$fetchState.pending">
    {{ field }}
    <v-col cols="12" md="6">
      <validation-provider #default="{errors}" name="relation type" rules="required">
        <v-select v-model="field.relationType" :error-messages="errors[0]" :items="fieldRelationTypes" dense item-text="label" item-value="code" label="Relation type" outlined></v-select>
      </validation-provider>
    </v-col>

    <v-col cols="12" md="6">
      <validation-provider #default="{errors}" name="targeted content" rules="required">
        <v-select v-model="field.relationContent" :error-messages="errors[0]" :items="contents" dense item-text="name" item-value="_id" label="Targeted content" outlined></v-select>
      </validation-provider>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Action, Component, mixins, State } from 'nuxt-property-decorator'
import { FieldParamsMixin } from '~/mixins/field-params.mixin'

@Component
export default class RelationParam extends mixins(FieldParamsMixin) {
  @State(state => state.list.fieldRelationTypes) fieldRelationTypes
  @State(state => state.contents) contents
  @Action('list/getFieldRelationTypes') getFieldRelationTypes

  async fetch () {
    await this.getFieldRelationTypes()
  }
}
</script>

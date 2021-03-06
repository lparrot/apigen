import { Component, Prop, VModel, Vue } from 'nuxt-property-decorator'

@Component
export class FieldParamsMixin extends Vue {
  @VModel() field!: any
  @Prop() mode!: 'add' | 'edit'

  async created () {
    if (this.field.params == null) {
      this.$set(this.field, 'params', {})
    }
  }
}

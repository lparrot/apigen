import Vue from 'vue'
import { extend, ValidationObserver, ValidationProvider } from 'vee-validate'
import * as rules from 'vee-validate/dist/rules'

export default async () => {
  Vue.component('ValidationObserver', ValidationObserver)
  Vue.component('ValidationProvider', ValidationProvider)

  Object.keys(rules).forEach(rule => {
    extend(rule, rules[rule])
  })
}

import { Context } from '@nuxt/types'
import Vue from 'vue'
import ConfirmDialog from '~/components/shared/ConfirmDialog.vue'

export class ConfirmDialogService {

  private _ctx: Context
  private _instance: ConfirmDialog

  constructor (ctx: Context) {
    this._ctx = ctx

    this._instance = new Vue(ConfirmDialog)
    this._instance.$vuetify = Object.assign({}, this._ctx.app.vuetify.framework)

    document.body.appendChild(this._instance.$mount().$el)
  }

  async open (title: string, message: string, options?: ConfirmDialogOption): Promise<boolean> {
    return await this._instance.open(title, message, options)
  }


}

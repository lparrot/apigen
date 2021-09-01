import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiContentField extends AbstractRepository {
  getAll () {
    return this._axios.$get(`/app/fields`)
  }

  getById (id: string) {
    return this._axios.$get(`/app/fields/${ id }`)
  }

  private static _entityToPojo ({ contentId, name, type, nullable, params, relationType, relationContent }) {
    let paramsToSend = null
    if (Object.keys(params).length < 1) {
      paramsToSend = params
    }
    return {
      contentId,
      name,
      type,
      nullable,
      params: paramsToSend,
      relationType,
      relationContent,
    }
  }

  create (body) {
    return this._axios.$post(`/app/fields`, ApiContentField._entityToPojo(body))
  }

  remove (id) {
    return this._axios.$delete(`/app/fields/${ id }`)
  }

  update (id, body) {
    return this._axios.$put(`/app/fields/${ id }`, ApiContentField._entityToPojo(body))
  }
}

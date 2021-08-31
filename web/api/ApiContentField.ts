import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiContentField extends AbstractRepository {
  getAll () {
    return this._axios.$get(`/app/fields`)
  }

  getById (id: string) {
    return this._axios.$get(`/app/fields/${ id }`)
  }

  private static _entityToPojo ({ contentId, name, type, nullable, params, relationType, relationContent }) {
    console.log(params)
    console.log(Object.keys(params).length)
    if (Object.keys(params).length < 1) {
      params = null
    }
    return {
      contentId,
      name,
      type,
      nullable,
      params,
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

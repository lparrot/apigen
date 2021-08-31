import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiContent extends AbstractRepository {
  getAll () {
    return this._axios.$get(`/app/contents`)
  }

  getById (id: string) {
    return this._axios.$get(`/app/contents/${ id }`)
  }

  create (body) {
    return this._axios.$post(`/app/contents`, ApiContent._entityToPojo(body))
  }

  remove (id) {
    return this._axios.$delete(`/app/contents/${ id }`)
  }

  update (id, body) {
    return this._axios.$put(`/app/contents/${ id }`, ApiContent._entityToPojo(body))
  }

  private static _entityToPojo ({ name, displayedField }) {
    return {
      name,
      displayedField,
    }
  }
}

import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiContentField extends AbstractRepository {
  getAll () {
    return this._axios.$get(`/app/fields`)
  }

  getById (id: string) {
    return this._axios.$get(`/app/fields/${ id }`)
  }

  create (body) {
    return this._axios.$post(`/app/fields`, body)
  }

  update (id, body) {
    return this._axios.$put(`/app/fields/${ id }`, body)
  }

  remove (id) {
    return this._axios.$delete(`/app/fields/${ id }`)
  }
}

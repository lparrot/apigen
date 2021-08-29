import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiContent extends AbstractRepository {
  getAll () {
    return this._axios.$get(`/app/contents`)
  }

  getById (id: string) {
    return this._axios.$get(`/app/contents/${ id }`)
  }

  create (body) {
    return this._axios.$post(`/app/contents`, body)
  }

  update (id, body) {
    return this._axios.$put(`/app/contents/${ id }`, body)
  }

  remove (id) {
    return this._axios.$delete(`/app/contents/${ id }`)
  }
}

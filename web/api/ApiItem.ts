import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiItem extends AbstractRepository {
  getAll (slug: string, pagination) {
    return this._axios.$get(`/${ slug }`, { params: { ...pagination } })
  }

  getById (slug: string, idItem: string) {
    return this._axios.$get(`/${ slug }/${ idItem }`)
  }

  create (slug: string, body) {
    return this._axios.$post(`/${ slug }`, body)
  }

  update (slug: string, idItem: string, body) {
    return this._axios.$put(`/${ slug }/${ idItem }`, body)
  }

  remove (slug: string, idItem: string) {
    return this._axios.$delete(`/${ slug }/${ idItem }`)
  }
}

import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiRelation extends AbstractRepository {
  getAllData (idContent: string) {
    return this._axios.$get(`/app/relations/${ idContent }`)
  }
}

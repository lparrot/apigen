import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiList extends AbstractRepository {
  getAllFieldTypes () {
    return this._axios.$get(`/app/lists/field_types`)
  }

  getAllFieldRelationTypes () {
    return this._axios.$get(`/app/lists/field_relation_types`)
  }
}

import { AbstractRepository } from '@/api/AbstractRepository'

export class ApiAdmin extends AbstractRepository {
  getCpuUsage () {
    return this._axios.$get('/app/admin/cpu_usage')
  }

  getJvmInfos (heap: boolean = true) {
    return this._axios.$get(`/app/admin/jvm?heap=${ heap }`)
  }

  getThreadInfos () {
    return this._axios.$get('/app/admin/threads')
  }

  getAuditEvents () {
    return this._axios.$get('/app/actuator/auditevents')
  }

  getLogs () {
    return this._axios.$get('/app/actuator/logfile')
  }

  getMetrics (name?: string) {
    if (name != null) {
      return this._axios.$get(`/app/actuator/metrics/${ name }`)
    }
    return this._axios.$get('/app/actuator/metrics')
  }

  restartApplication () {
    return this._axios.$post('/app/admin/restart')
  }

  getScheduledTasks () {
    return this._axios.$get('/app/actuator/scheduledtasks')
  }

  getHttpTraces () {
    return this._axios.$get('/app/actuator/httptrace')
  }
}

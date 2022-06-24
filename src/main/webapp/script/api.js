import Requests from './requests.js';

class VisolApi {
  static portId = 1;
  static get;

  static getTerminals = () =>
    Requests.getData(`/ports/${VisolApi.portId}/terminals`);

  static getBerthsPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/berths`);

  static getSchedulesPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/schedules`);

  static getVesselsPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/vessels`);

  static postVessel = (vessel) =>
    Requests.postData('/vessels', vessel);

  static putVessel = (vesselId, vessel) =>
    Requests.putData(`/vessels/${vesselId}`, vessel);

  static putSchedule = (vesselId, schedule) =>
    Requests.putData(`/vessels/${vesselId}/schedule`, schedule);

  static formatDatetimeForApi = (t) => new Date(Date.parse(t)).toISOString().substring(0, 19) + 'Z';

  static formatDatetimeForInput(date) {
    const timezoneOffset = new Date().getTimezoneOffset() * 60000; // offset in milliseconds
    return new Date(date - timezoneOffset).toJSON().substring(0, 16);
  }
}

export default VisolApi;

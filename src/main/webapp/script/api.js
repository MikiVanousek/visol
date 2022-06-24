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
}

export default VisolApi;

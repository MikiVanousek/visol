class Time {
  constructor(value) {
    if (typeof value === 'string') {
      const numbers = value.split(':').map(Number);
      if (numbers.length !== 3) {
        throw new Error('Invalid time string');
      }
      this._hours = numbers[0];
      this._minutes = numbers[1];
      this._seconds = numbers[2];
    } else if (typeof value === 'object') {
      // Assume it's a Date object
      this._hours = value.getHours();
      this._minutes = value.getMinutes();
      this._seconds = value.getSeconds();
    }

    // Note that in PostgreSQL, 24:00:00 is considered to be 00:00:00.
    if (this._hours < 0 || this._hours > 23 ||
      this._minutes < 0 || this._minutes > 59 ||
      this._seconds < 0 || this._seconds > 59) {
      throw new Error('Invalid time');
    }
  }

  get hours() {
    return this._hours;
  }

  get minutes() {
    return this._minutes;
  }

  get seconds() {
    return this._seconds;
  }

  get value() {
    return this._hours + this._minutes / 60 + this._seconds / 3600;
  }

  formatted(format = 'hh:mm:ss') {
    let result = format;
    result = result.replace('hh', this._hours.toString().padStart(2, '0'));
    result = result.replace('h', this._hours.toString());
    result = result.replace('mm', this._minutes.toString().padStart(2, '0'));
    result = result.replace('m', this._minutes.toString());
    result = result.replace('ss', this._seconds.toString().padStart(2, '0'));
    result = result.replace('s', this._seconds.toString());
    return result;
  }
}

export default Time;

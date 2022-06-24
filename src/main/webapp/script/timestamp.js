class Timestamp {
  constructor(value) {
    if (value === undefined) {
      value = new Date();
    }

    if (typeof value === 'string') {
      const numbers = value.split(/(?<!^)[-:TZ]/)
          .filter((s) => s.length > 0).map(Number);
      if (numbers.length < 4 || numbers.length > 6) {
        throw new Error('Invalid timestamp string');
      }
      this._year = numbers[0];
      this._month = numbers[1];
      this._day = numbers[2];
      this._hours = numbers[3];
      this._minutes = numbers[4] || 0;
      this._seconds = numbers[5] || 0;
    } else if (value instanceof Date) {
      this._year = value.getUTCFullYear();
      this._month = value.getUTCMonth() + 1;
      this._day = value.getUTCDate();
      this._hours = value.getUTCHours();
      this._minutes = value.getUTCMinutes();
      this._seconds = value.getUTCSeconds();
    } else {
      throw new Error('Invalid timestamp argument');
    }

    // Note that in PostgreSQL, 24:00:00 is considered to be 00:00:00.
    if (this._month < 1 || this._month > 12 || this._day < 1 ||
        this._day > new Date(this._year, this._month, -1).getDate() ||
        this._hours < 0 || this._hours > 23 ||
        this._minutes < 0 || this._minutes > 59 ||
        this._seconds < 0 || this._seconds > 59) {
      throw new Error('Invalid timestamp values');
    }
  }

  get year() {
    return this._year;
  }

  get month() {
    return this._month;
  }

  get day() {
    return this._day;
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

  formatted(format = 'YYYY-MM-DDThh:mm:ssZ') {
    let result = format;
    result = result.replace('YYYY', this.year.toString());
    result = result.replace('MM', this.month.toString().padStart(2, '0'));
    result = result.replace('M', this.month.toString());
    result = result.replace('DD', this.day.toString().padStart(2, '0'));
    result = result.replace('D', this.day.toString());
    result = result.replace('hh', this.hours.toString().padStart(2, '0'));
    result = result.replace('h', this.hours.toString());
    result = result.replace('mm', this.minutes.toString().padStart(2, '0'));
    result = result.replace('m', this.minutes.toString());
    result = result.replace('ss', this.seconds.toString().padStart(2, '0'));
    result = result.replace('s', this.seconds.toString());
    return result;
  }

  toDate() {
    return new Date(Date.UTC(
        this.year,
        this.month - 1,
        this.day,
        this.hours,
        this.minutes,
        this.seconds,
    ));
  }

  toLocal() {
    // Subtract the timezone offset
    const date = this.toDate();
    const offset = new Date().getTimezoneOffset() * 60 * 1000;
    return new Timestamp(new Date(date.getTime() - offset));
  }

  toUTC() {
    // Add the timezone offset
    const date = this.toDate();
    const offset = new Date().getTimezoneOffset() * 60 * 1000;
    return new Timestamp(new Date(date.getTime() + offset));
  }
}

export default Timestamp;

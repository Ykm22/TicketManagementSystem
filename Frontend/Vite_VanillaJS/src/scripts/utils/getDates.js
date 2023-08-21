export function getDates(event){
    const startDate = new Date(event.startDate);
    const endDate = new Date(event.endDate);
    const [startDay, startTime] = separateDate(startDate);
    const [endDay, endTime] = separateDate(endDate);
    return [startDay, startTime, endDay, endTime];
}

export function separateDate(date){
    const year = date.getFullYear();
    let month = date.getMonth();
    if(parseInt(month) < 10) {
        month = `0${month}`;
    }
    let day = date.getDate();
    if(parseInt(day) < 10) {
        day = `0${day}`;
    }
    let hour = date.getUTCHours();
    if(parseInt(hour) < 10) {
        hour = `0${hour}`;
    }
    let minutes = date.getUTCMinutes();
    if(parseInt(minutes) < 10){
        minutes = `0${minutes}`;
    }
    return [`${year}-${month}-${day}`, `${hour}:${minutes}`];
}
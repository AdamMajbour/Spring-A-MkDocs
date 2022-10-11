const monthNames = ["STY", "LUTY", "MARZ", "KWI",
    "MAJ", "CZE", "LIP", "SIE",
    "WRZ", "PAŹ", "LIS", "GRU"];

curr =  new Date();
first = curr.getDate() - curr.getDay() + 1;
last = first + 6;

firstDay = new Date(curr.setDate(first));
dF = firstDay.getDate();
mF = firstDay.getMonth();
yF = firstDay.getFullYear();

lastDay = new Date(curr.setDate(last));
dL = lastDay.getDate();
mL = lastDay.getMonth();
yL = lastDay.getFullYear();

curFirstDay = new Date(firstDay);
curLastDay = new Date(lastDay);

// Aktualny tydzień
document.getElementById("weekDate").innerHTML = dF + " " + monthNames[mF] +
    " " + yF + " - " + dL + " " + monthNames[mL] + " " + yL;

var m = new Date();
m.setDate(firstDay.getDate());
var tu = new Date();
tu.setDate(firstDay.getDate()+1);
var w = new Date();
w.setDate(firstDay.getDate()+2);
var th = new Date();
th.setDate(firstDay.getDate()+3);
var f = new Date();
f.setDate(firstDay.getDate()+4);
var sa = new Date();
sa.setDate(firstDay.getDate()+5);
var su = new Date();
su.setDate(firstDay.getDate()+6);

// Dni tygodnia w górnej części kalendarza
setWeekDays(m, tu, w, th, f, sa, su);

var date = new Date();
var hour = date.getHours();
var min = date.getMinutes();
var day = date.getDay();
const hourIds = ["6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"];
const days = ["m", "tu", "w", "th", "f", "sa", "su"];

var id = days[day-1];
// Aktualny dzień tygodnia

document.getElementById(id).classList.add('today');

currentWeekDayId = String(id);

if (hour > 21 || hour < 6) id += hourIds[1]
else id += hourIds[hour-6];

if (min >= 30) id += ".5";
// Czerwona kreska wskazująca na aktualny czas

document.getElementById(id).classList.add('now');

setPast();
addEvents(curFirstDay, curLastDay);

function setPast()
{
    var i, j, marker=0;
    for(i=0; i<7; i++)
    {
        for (j=0; j<16; j++)
        {
            if (j != 0)
            {
                if(days[i] + hourIds[j] == id) marker = 1;

                if (marker==0) document.getElementById(days[i] + hourIds[j]).classList.add('past');
                else document.getElementById(days[i] + hourIds[j]).classList.remove('past');
            }

            if (days[i] + hourIds[j] + ".5" == id) marker = 1;

            if (marker==0) document.getElementById(days[i] + hourIds[j] + ".5").classList.add('past');
            else document.getElementById(days[i] + hourIds[j] + ".5").classList.remove('past');
        }
    }
}

function nextWeek()
{
    curFirstDay.setDate(curFirstDay.getDate()+7);
    curLastDay.setDate(curLastDay.getDate()+7);

    dF = curFirstDay.getDate();
    mF = curFirstDay.getMonth();
    yF = curFirstDay.getFullYear();

    dL = curLastDay.getDate();
    mL = curLastDay.getMonth();
    yL = curLastDay.getFullYear();

    // Aktualny tydzień
    document.getElementById("weekDate").innerHTML = dF + " " + monthNames[mF] +
        " " + yF + " - " + dL + " " + monthNames[mL] + " " + yL;

    m.setDate(m.getDate()+7);
    tu.setDate(tu.getDate()+7);
    w.setDate(w.getDate()+7);
    th.setDate(th.getDate()+7);
    f.setDate(f.getDate()+7);
    sa.setDate(sa.getDate()+7);
    su.setDate(su.getDate()+7);

    // Dni tygodnia w górnej części kalendarza
    setWeekDays(m, tu, w, th, f, sa, su);

    curFirstDay.setHours(0, 0, 0, 0);
    firstDay.setHours(0, 0, 0, 0);

    if (curFirstDay.getTime()==firstDay.getTime())
    {
        document.getElementById(currentWeekDayId).classList.add('today');
        document.getElementById(id).classList.add('now');
        setPast();
    }
    else
    {
        document.getElementById(currentWeekDayId).classList.remove('today');
        colorCalendarDays(curFirstDay);
    }

    addEvents(curFirstDay, curLastDay);
}

function previousWeek()
{
    curFirstDay.setDate(curFirstDay.getDate()-7);
    curLastDay.setDate(curLastDay.getDate()-7);

    dF = curFirstDay.getDate();
    mF = curFirstDay.getMonth();
    yF = curFirstDay.getFullYear();

    dL = curLastDay.getDate();
    mL = curLastDay.getMonth();
    yL = curLastDay.getFullYear();

    // Aktualny tydzień
    document.getElementById("weekDate").innerHTML = dF + " " + monthNames[mF] +
        " " + yF + " - " + dL + " " + monthNames[mL] + " " + yL;

    m.setDate(m.getDate()-7);
    tu.setDate(tu.getDate()-7);
    w.setDate(w.getDate()-7);
    th.setDate(th.getDate()-7);
    f.setDate(f.getDate()-7);
    sa.setDate(sa.getDate()-7);
    su.setDate(su.getDate()-7);

    // Dni tygodnia w górnej części kalendarza
    setWeekDays(m, tu, w, th, f, sa, su);

    curFirstDay.setHours(0, 0, 0, 0);
    firstDay.setHours(0, 0, 0, 0);

    if (curFirstDay.getTime()==firstDay.getTime())
    {
        document.getElementById(currentWeekDayId).classList.add('today');
        document.getElementById(id).classList.add('now');
        setPast();
    }
    else
    {
        document.getElementById(currentWeekDayId).classList.remove('today');
        colorCalendarDays(curFirstDay);
    }

    addEvents(curFirstDay, curLastDay);
}

function setCurrentWeek()
{
    today = new Date();

    first = today.getDate() - today.getDay() + 1;
    last = first + 6;

    curFirstDay = new Date(today.setDate(first));
    curLastDay = new Date(today.setDate(last));

    dF = curFirstDay.getDate();
    mF = curFirstDay.getMonth();
    yF = curFirstDay.getFullYear();

    dL = curLastDay.getDate();
    mL = curLastDay.getMonth();
    yL = curLastDay.getFullYear();

    // Aktualny tydzień
    document.getElementById("weekDate").innerHTML = dF + " " + monthNames[mF] +
        " " + yF + " - " + dL + " " + monthNames[mL] + " " + yL;

    m.setDate(curFirstDay.getDate());
    tu.setDate(curFirstDay.getDate()+1);
    w.setDate(curFirstDay.getDate()+2);
    th.setDate(curFirstDay.getDate()+3);
    f.setDate(curFirstDay.getDate()+4);
    sa = new Date(curFirstDay);
    sa.setDate(curFirstDay.getDate()+5);
    su = new Date(curFirstDay);
    su.setDate(curFirstDay.getDate()+6);

    setWeekDays(m, tu, w, th, f, sa, su);

    document.getElementById(currentWeekDayId).classList.add('today');
    document.getElementById(id).classList.add('now');
    setPast();
    addEvents(curFirstDay, curLastDay);
}

function setWeekDays(m, tu, w, th, f, sa, su)
{
    document.getElementById("m").innerHTML = "Pon, " + m.getDate();
    document.getElementById("tu").innerHTML = "Wt, " + tu.getDate();
    document.getElementById("w").innerHTML = "Śr, " + w.getDate();
    document.getElementById("th").innerHTML = "Czw, " + th.getDate();
    document.getElementById("f").innerHTML = "Pią, " + f.getDate();
    document.getElementById("sa").innerHTML = "Sob, " + sa.getDate();
    document.getElementById("su").innerHTML = "Niedz, " + su.getDate();
}

function colorCalendarDays(curFirstDay)
{
    if (curFirstDay>firstDay)
    {
        var i, j;
        for(i=0; i<7; i++)
        {
            for (j=0; j<16; j++)
            {
                if (j != 0)
                {
                    document.getElementById(days[i] + hourIds[j]).classList.remove('past');
                    document.getElementById(days[i] + hourIds[j]).classList.remove('now');
                }
                document.getElementById(days[i] + hourIds[j] + ".5").classList.remove('past');
                document.getElementById(days[i] + hourIds[j] + ".5").classList.remove('now');
            }
        }
    }else if (curFirstDay<firstDay)
    {
        var i, j;
        for(i=0; i<7; i++)
        {
            for (j=0; j<16; j++)
            {
                if (j != 0)
                {
                    document.getElementById(days[i] + hourIds[j]).classList.add('past');
                    document.getElementById(days[i] + hourIds[j]).classList.remove('now');
                }
                document.getElementById(days[i] + hourIds[j] + ".5").classList.add('past');
                document.getElementById(days[i] + hourIds[j] + ".5").classList.remove('now');
            }
        }
    }
}

function addEvents(firstDay, lastDay)
{
    var divs = document.querySelectorAll('div');

    [].forEach.call(divs, function(div) {
        // do whatever
        div.classList.remove("event");
        var x = document.getElementsByClassName("eventContent");
        for (i = 0; i < x.length; i++) {
            x[i].remove();
        }
    });

    fetch('http://localhost:8081/calendar')
        .then(response => response.json())
        .then(function (data){
            return data.map(function (training){
                var fromDate = new Date(training.fromDate.date.year, training.fromDate.date.month-1,
                    training.fromDate.date.day, training.fromDate.time.hour, training.fromDate.time.minute);

                var toDate = new Date(training.toDate.date.year, training.toDate.date.month-1, training.toDate.date.day,
                    training.toDate.time.hour, training.toDate.time.minute);

                if (fromDate > firstDay && fromDate < lastDay)
                {
                    var tid = "";

                    switch (fromDate.getDay()){
                        case 1:
                            tid += "m";
                            break;
                        case 2:
                            tid += "tu";
                            break;
                        case 3:
                            tid += "w";
                            break;
                        case 4:
                            tid += "th";
                            break;
                        case 5:
                            tid += "f";
                            break;
                        case 6:
                            tid += "sa";
                            break;
                        case 7:
                            tid += "su";
                            break;
                    }

                    tid += training.fromDate.time.hour;

                    if (training.fromDate.time.minute >= 30) tid += ".5";

                    var node = document.createElement("DIV");
                    node.className = "eventContent";

                    var textnode = document.createTextNode(training.fromDate.time.hour);

                    if (training.fromDate.time.minute < 10) textnode.textContent += ":0";
                    else textnode.textContent += ":";

                    textnode.textContent += training.fromDate.time.minute + " - " + training.toDate.time.hour;

                    if (training.toDate.time.minute < 10) textnode.textContent += ":0";
                    else textnode.textContent += ":";

                    textnode.textContent += training.toDate.time.minute + " " + training.name;

                    var duration = toDate.getTime()-fromDate.getTime();

                    node.appendChild(textnode);
                    node.classList.add("event");
                    fromDate.setHours(0,0,0,0);
                    let today = new Date();
                    today.setHours(0,0,0,0);
                    if (today.getTime() === fromDate.getTime()) node.style.boxShadow = "10px 10px 5px 1px red";

                    duration = duration/(1000*3600);

                    var height = 210 * duration;
                    console.log(height);

                    node.style.height = height + "%";

                    document.getElementById(tid).appendChild(node);
                }
            })
        });
}
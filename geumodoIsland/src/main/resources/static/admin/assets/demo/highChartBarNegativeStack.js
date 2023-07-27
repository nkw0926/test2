Highcharts.Templating.helpers.abs = value => Math.abs(value);

const categories = [
    '20-24', '25-29', '30-34', '35-39', 'old'
];

function fetchDataFromServerByAgeRangeSex() {
    fetch("/admin/agerange/sex/barChart")
        .then((response) => response.json())
        .then((data) => {
            drawBarChartByAgeRangeSex(data);
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}

function drawBarChartByAgeRangeSex(data) {
    const maleData = data[1];
    const femaleData = data[0];

    Highcharts.chart('ageRangeSexBarChart', {
        chart: {
            type: 'bar'
        },
        title: {
            text: '성별 / 나이 분포도',
            align: 'left'
        },
        subtitle: {
            text: 'Source: <a ' +
                'href="https://countryeconomy.com/demography/population-structure/somalia"' +
                'target="_blank">countryeconomy.com</a>',
            align: 'left'
        },
        accessibility: {
            point: {
                valueDescriptionFormat: '{index}. Age {xDescription}, {value}%.'
            }
        },
        xAxis: [{
            categories: categories,
            reversed: false,
            labels: {
                step: 1
            },
            accessibility: {
                description: 'Age (male)'
            }
        }, { // mirror axis on right side
            opposite: true,
            reversed: false,
            categories: categories,
            linkedTo: 0,
            labels: {
                step: 1
            },
            accessibility: {
                description: 'Age (female)'
            }
        }],
        yAxis: {
            title: {
                text: null
            },
            labels: {
                format: '{abs value}'
            },
            accessibility: {
                description: 'Percentage population',
                rangeDescription: 'Range: 0 to 5%'
            }
        },

        plotOptions: {
            series: {
                stacking: 'normal',
                borderRadius: '50%'
            }
        },

        tooltip: {
            format: '<b>{series.name}, age {point.category}</b><br/>' +
                'Population: {(abs point.y):.1f}%'
        },

        series: [{
            name: 'Male',
            data: maleData
        }, {
            name: 'Female',
            data: femaleData
        }]
    });
}

$(document).ready(function () {
    fetchDataFromServerByAgeRangeSex();
});


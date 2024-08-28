/**
 * Component DisplayareaComponent is defined as
 * `<e-displayarea-component>`
 *
 * Imperatively create component
 * @example
 * let component = new DisplayareaComponent();
 *
 * Declaratively create component
 * @example
 * <e-displayarea-component></e-displayarea-component>
 *
 * @extends {LitComponent}
 */

import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './displayareaComponent.css';
import '@eui/table';
import '@eui/layout';
import '@eui/base';
import * as d3 from 'd3';
import { json } from 'd3';

/**
 * @property {Boolean} propOne - show active/inactive state.
 * @property {String} propTwo - shows the "Hello World" string.
 */
@definition('e-displayarea-component', {
  style,
  home: 'displayarea-component',
  props: {
    propOne: { attribute: true, type: Boolean },
    propTwo: { attribute: true, type: String, default: 'Hello World' },
    from: { attribute: true, type: String},
    to: { attribute: true, type: String},
  },
})


export default class DisplayareaComponent extends LitComponent {

  doGraph(jsonData) {
    const json = jsonData;

    var margin = {top: 10, right: 100, bottom: 30, left: 30},
    width = 800- margin.left - margin.right,
    height = 800 - margin.top - margin.bottom;
    const body = document.querySelector('body')
    const euiContainer = body.querySelector('eui-container')
    // var parseTime = d3.timeParse('%a %d');

    function findAllDeep(parent, selectors, depth = null) {
      let nodes = new Set();
      let currentDepth = 1
        ;
      const recordResult = (nodesArray) => {
        for (const node of nodesArray) {
          nodes.add(node)
        }
      }
      const recursiveSeek = _parent => {
        // check for selectors in lightdom
        recordResult(_parent.querySelectorAll(selectors));
        if (_parent.shadowRoot) {
          // check for selectors in shadowRoot
          recordResult(_parent.shadowRoot.querySelectorAll(selectors));
          // look for nested components with shadowRoots
          for (let child of [..._parent.shadowRoot.querySelectorAll('*')].filter(i => i.shadowRoot)) {
            // make sure we haven't hit our depth limit
            if (depth === null || currentDepth < depth) {
              recursiveSeek(child);
            }
          }
        }
      };
      recursiveSeek(parent);
      const [first] = nodes
      first.innerHTML = '';
      return first;
    };
    var local = findAllDeep(euiContainer, `[id="my_dataviz"]`, 20); //getShadowRoot(eDependencyGraphComponent, "#my_dataviz")

    var svg = d3.select(local)
    .append("svg")
      .attr("width", width + margin.left + margin.right)
      .attr("height", height + margin.top + margin.bottom)
    .append("g")
      .attr("transform",
            "translate(" + margin.left + "," + margin.top + ")");


    function createChart(data) {
    var allGroup = ["valueA", "valueB", "valueC"]
    let count = Object.keys(data).length;
    let max = 0;
    for(let x in data){
      max = Math.max(max, Math.round(data[x].valueA), Math.round(data[x].valueB), Math.round(data[x].valueC))
    }
    
    // Reformat the data: we need an array of arrays of {x, y} tuples
    var dataReady = allGroup.map( function(grpName) { 
      // .map allows to do something for each element of the list
      return {
        name: grpName,
        values: data.map(function(d) {
          return {time: d.time, value: +d[grpName]};
        })
      };
    });

    
    // I strongly advise to have a look to dataReady with
    // console.log(dataReady)

    // A color scale: one color for each group
    var myColor = d3.scaleOrdinal()
      .domain(allGroup)
      .range(d3.schemeSet2);

    // Add X axis --> it is a date format
    var x = d3.scaleLinear()
      .domain([0, count])
      .range([ 0, width ]);
    svg.append("g")
      .attr("transform", "translate(0," + height + ")")
      .call(d3.axisBottom(x));

    // Add Y axis
    var y = d3.scaleLinear()
      .domain( [0,max])
      .range([ height, 0 ]);
    svg.append("g")
      .call(d3.axisLeft(y));

    // Add the lines
    var line = d3.line()
      .x(function(d) { return x(+d.time) })
      .y(function(d) { return y(+d.value) })
    svg.selectAll("myLines")
      .data(dataReady)
      .enter()
      .append("path")
        .attr("d", function(d){ return line(d.values) } )
        .attr("stroke", function(d){ return myColor(d.name) })
        .style("stroke-width", 4)
        .style("fill", "none")

    // Add the points
    svg
      // First we need to enter in a group
      .selectAll("myDots")
      .data(dataReady)
      .enter()
        .append('g')
        .style("fill", function(d){ return myColor(d.name) })
      // Second we need to enter in the 'values' part of this group
      .selectAll("myPoints")
      .data(function(d){ return d.values })
      .enter()
      .append("circle")
        .attr("cx", function(d) { return x(d.time) } )
        .attr("cy", function(d) { return y(d.value) } )
        .attr("r", 5)
        .attr("stroke", "white")

    // Add a legend at the end of each line
    svg
      .selectAll("myLabels")
      .data(dataReady)
      .enter()
        .append('g')
        .append("text")
          .datum(function(d) { return {name: d.name, value: d.values[d.values.length - 1]}; }) // keep only the last value of each time series
          .attr("transform", function(d) { return "translate(" + x(d.value.time) + "," + y(d.value.value) + ")"; }) // Put the text at the position of the last point
          .attr("x", 12) // shift the text a bit more right
          .text(function(d) { return d.name; })
          .style("fill", function(d){ return myColor(d.name) })
          .style("font-size", 15)


    }

    createChart(json)
    // this.executeRender();
  }


  unstableBuildsData = [];


  constructor() {
    super();
    this.loadNames();
    this.loadStats();
  }

  async _makeData(){
    console.log("Called");
    // TODO: error checking Should not be same days Should not be 
    let failuredata = [];
    let localfrom = new Date(this.from);
    let localto = new Date(this.to);
    let localloop = localfrom;
    let count = 1;
    while (localloop < localto) {
      let prevDate = localloop.getTime();
      let prevform = new Date(prevDate);
      let newDate = localloop.setDate(localloop.getDate() + 1);
      let nextDate = new Date(newDate);
      // make calls to backend to get failure rate
      let nextDatets = nextDate.valueOf();
      const object = { "timestampFrom": prevDate, "timestampTo": nextDatets };
      const jsonobj = JSON.stringify(object);
      let answer = new Promise((res, rej) => {
        fetch('http://localhost:8080/build-recovery-time-v2/jobs/time-period', {
          method: 'POST',
          body: jsonobj,
          headers: { 'Content-Type': 'application/json' }
        }).then(res => res.json())
          .then(data => { res(data) })
          .catch(error => rej(error))
      })

      let recoveryTime = await answer;
      recoveryTime.medianInMinutes = recoveryTime.medianInMinutes < 0 ? recoveryTime.medianInMinutes * -1 : recoveryTime.medianInMinutes;

      answer = new Promise((res, rej) => {
        fetch('http://localhost:8080/build-failure-rate/jobs/time-period', {
          method: 'POST',
          body: jsonobj,
          headers: { 'Content-Type': 'application/json' }
        }).then(res => res.json())
          .then(data => { res(data) })
          .catch(error => rej(error))
      })

      let failureRate = await answer;
      failureRate = failureRate == -1 ? 0 : failureRate;
      failuredata.push({
        "time": count,
        "valueA": failureRate,
        "valueB": recoveryTime.medianInMinutes,
        "valueC": recoveryTime.standardDeviationInMinutes
      });
      count++;
    }
    return failuredata;
  }


  async loadNames() {
    fetch('http://localhost:8080/build-failure-rate/jobs')
      .then(function (response) {
        return response.json();
      })
      .then(function (data) {
        this.unstableBuildsData.push({ col1: 'Build Failure Rate', col2: data, colorBand: 'red' });
        this.executeRender();
      }
        .bind(this))
      .catch(function (e) {
        console.log("error")
      })
  }

  async loadStats(){
    fetch('http://localhost:8080/build-recovery-time-v2/jobs')
      .then(function (response) {
        return response.json();
      })
      .then(function (data) {
        this.unstableBuildsData.push({ col1: 'Median Build Failure Recovery Time', col2: data.medianInMinutes, colorBand: 'red' },
        { col1: 'Variation in Build Failure Recovery Time', col2: data.standardDeviationInMinutes, colorBand: 'red'});
        this.executeRender();
      }
        .bind(this))
      .catch(function (e) {
        console.log("error")
      })
  }

  render() {
    const unstableBuildsColumns = [
      { title: 'Metric', attribute: 'col1' },
      { title: 'Result', attribute: 'col2' }
    ];

    return html`
    <eui-datepicker></eui-datepicker>
    
    <div class="layout__dashboard" id="tamz">
    
      <!-- UNSTABLE BUILDS TABLE -->
      <eui-layout-v0-tile tile-title="Builds" column=0>
        <div slot="content">
          <eui-table-v0 .columns=${unstableBuildsColumns} .data=${this.unstableBuildsData}></eui-table-v0>
        </div>
    
        <!-- CALENDAR -->
      </eui-layout-v0-tile>
      <eui-layout-v0-tile column=0>
        <div slot="content">
          <h3>From</h3>
          <eui-base-v0-datepicker @eui-datepicker:change="${(event) => {
            let date = event.detail.date;
            this.from = date;
          }}">
          </eui-base-v0-datepicker>
          <h3>To</h3>
          <eui-base-v0-datepicker @eui-datepicker:change="${(event) => {
            let date = event.detail.date;
            this.to = date;
          }}">
          </eui-base-v0-datepicker>
          <eui-base-v0-button @click=${() => {
            this._makeData().then(res => this.doGraph(res));
          }}>Apply</eui-base-v0-button>
        </div>
    
    
      </eui-layout-v0-tile>
    
      <!-- CHART -->
      <eui-layout-v0-tile tile-title="CHART" column=0 column-span=2>
        <div slot="content">
          <div slot="content" id="my_dataviz"></div>
          <p>Value A (green) -> Build Failure Rate (In Percentage) </p> 
          <p>Value B (orange) -> Median Build Recovery Time (In Minutes) </p>
          <p>Value C (blue) -> Variation Build Recovery Time (In Minutes)  </p> 
        </div>
      </eui-layout-v0-tile>
    </div>`;
  }
}

/**
 * Register the component as e-displayarea-component.
 * Registration can be done at a later time and with a different name
 */
DisplayareaComponent.register();

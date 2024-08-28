/**
 * Component Job5MainComponent is defined as
 * `<e-job5-main-component>`
 *
 * Imperatively create component
 * @example
 * let component = new Job5MainComponent();
 *
 * Declaratively create component
 * @example
 * <e-job5-main-component></e-job5-main-component>
 *
 * @extends {LitComponent}
 */
import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './job5MainComponent.css';
import '@eui/layout';
import 'job1component';
import 'panel-component';
import '@eui/table';

/**
 * @property {Boolean} propOne - show active/inactive state.
 * @property {String} propTwo - shows the "Hello World" string.
 */
@definition('e-job5-main-component', {
  style,
  home: 'job5-main-component',
  props: {
    propOne: { attribute: true, type: Boolean },
    propTwo: { attribute: true, type: String, default: 'Hello World' },
  },
})
export default class Job5MainComponent extends LitComponent {
  showJob1Content = false;
  showJob2Content = false;
  showJob3Content = false;
  showJob4Content = false;
  showJob5Content = false;
  showJob6Content = false;
  showJob7Content = false;
  showJob8Content = false;
  showJob9Content = false;
  showJob10Content = false;

  _loadJob1Data() {
   this.showJob1Content = true;
   this.executeRender();
 }

  _loadJob2Data() {
    this.showJob2Content = true;
    this.executeRender();
  }

  _loadJob3Data() {
   this.showJob3Content = true;
   this.executeRender();
 }

 _loadJob4Data() {
   this.showJob4Content = true;
   this.executeRender();
 }

 _loadJob5Data() {
   this.showJob5Content = true;
   this.executeRender();
 }

 _loadJob6Data() {
   this.showJob6Content = true;
   this.executeRender();
 }

 _loadJob7Data() {
   this.showJob7Content = true;
   this.executeRender();
 }

 _loadJob8Data() {
   this.showJob8Content = true;
   this.executeRender();
 }

 _loadJob9Data() {
   this.showJob9Content = true;
   this.executeRender();
 }

 _loadJob10Data() {
   this.showJob10Content = true;
   this.executeRender();
 }

   render() {
    const unstableBuildsColumns = [
      { title: 'Metric', attribute: 'col1' },
      { title: 'Result', attribute: 'col2' }
    ];
    const unstableBuildsData = [
      { col1: 'Build Failure Rate', col2: 'TAMI', colorBand: 'red' },
      { col1: 'Average Build Failure Recovery Time', col2: 'High', colorBand: 'red' },
      { col1: 'Vacation in Build Failure Recovery Time', col2: 'High', colorBand: 'red' }
    ];

    const stableBuildsColumns = [
      { title: 'Metric', attribute: 'col1' },
      { title: 'Result', attribute: 'col2' }
    ];
    const stableBuildsData = [
      { col1: 'Build Failure Rate', col2: 'Low', colorBand: 'green' },
      { col1: 'Average Build Failure Recovery Time', col2: 'Low', colorBand: 'green' },
      { col1: 'Vacation in Build Failure Recovery Time', col2: 'Low', colorBand: 'green' }
    ];

    
    if (this.showJob1Content) {
      return html `<e-main-component></e-main-component>`
    }
    if (this.showJob3Content) {
      return html `<e-job3-main-component></e-job3-main-component>`
    }
    if (this.showJob2Content) {
      return html `<e-job2-main-component></e-job2-main-component>`
    }
    if (this.showJob4Content) {
      return html `<e-job4-main-component></e-job4-main-component>`
    }
    if (this.showJob6Content) {
      return html `<e-job6-main-component></e-job6-main-component>`
    }
    if (this.showJob7Content) {
      return html `<e-job7-main-component></e-job7-main-component>`
    }
    if (this.showJob8Content) {
      return html `<e-job8-main-component></e-job8-main-component>`
    }
    if (this.showJob9Content) {
      return html `<e-job9-main-component></e-job9-main-component>`
    }
    if (this.showJob10Content) {
      return html `<e-job10-main-component></e-job10-main-component>`
    }
    else {
      return html`<eui-layout-v0-multi-panel-tile tile-title="Build Stabilty Indicator">

<div slot="content">

    <h2>Job 4</h2>  
    <e-job5-displayarea-component></e-job5-displayarea-component>
      
    </div>
  
    <eui-layout-v0-tile-panel
      id="filter"
      tile-title="Jobs"
      slot="left"
      icon-name="filter"
      width=400
    >
      <div slot="content">
  
      <eui-base-v0-menu-item value="job-1" label="ALL JOBS" @eui-menuItem:click="${(event) => this._loadJob1Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-2" label="Job 1" @eui-menuItem:click="${(event) => this._loadJob2Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-3" label="Job 2" @eui-menuItem:click="${(event) => this._loadJob3Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-4" label="Job 3" @eui-menuItem:click="${(event) => this._loadJob4Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-5" label="Job 4" @eui-menuItem:click="${(event) => this._loadJob5Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-6" label="Job 5" @eui-menuItem:click="${(event) => this._loadJob6Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-7" label="Job 6" @eui-menuItem:click="${(event) => this._loadJob7Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-8" label="Job 7" @eui-menuItem:click="${(event) => this._loadJob8Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-9" label="Job 8" @eui-menuItem:click="${(event) => this._loadJob9Data()}"></eui-base-v0-menu-item>
      <eui-base-v0-menu-item value="job-10" label="Job 9" @eui-menuItem:click="${(event) => this._loadJob10Data()}"></eui-base-v0-menu-item>
      </div>
    </eui-layout-v0-tile-panel>
    
    `;
    }
  }
}

/**
 * Register the component as e-job5-main-component.
 * Registration can be done at a later time and with a different name
 */
Job5MainComponent.register();

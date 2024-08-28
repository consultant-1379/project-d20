/**
 * Component Job1component is defined as
 * `<e-job1component>`
 *
 * Imperatively create component
 * @example
 * let component = new Job1component();
 *
 * Declaratively create component
 * @example
 * <e-job1component></e-job1component>
 *
 * @extends {LitComponent}
 */
import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './job1component.css';

/**
 * @property {Boolean} propOne - show active/inactive state.
 * @property {String} propTwo - shows the "Hello World" string.
 */
@definition('e-job1component', {
  style,
  home: 'job1component',
  props: {
    propOne: { attribute: true, type: Boolean },
    propTwo: { attribute: true, type: String, default: 'Hello World' },
  },
})
export default class Job1component extends LitComponent {
  /**
   * Render the <e-job1component> component. This function is called each time a
   * prop changes.
   */
  render() {
    return html`<h1>ALL JOBS</h1>`;
  }
}

/**
 * Register the component as e-job1component.
 * Registration can be done at a later time and with a different name
 */
Job1component.register();

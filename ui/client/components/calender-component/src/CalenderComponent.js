/**
 * Component CalenderComponent is defined as
 * `<e-calender-component>`
 *
 * Imperatively create component
 * @example
 * let component = new CalenderComponent();
 *
 * Declaratively create component
 * @example
 * <e-calender-component></e-calender-component>
 *
 * @extends {LitComponent}
 */
import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './calenderComponent.css';
import '@eui/base';

/**
 * @property {Boolean} propOne - show active/inactive state.
 * @property {String} propTwo - shows the "Hello World" string.
 */
@definition('e-calender-component', {
  style,
  home: 'calender-component',
  props: {
    propOne: { attribute: true, type: Boolean },
    propTwo: { attribute: true, type: String, default: 'Hello World' },
  },
})
export default class CalenderComponent extends LitComponent {
  /**
   * Render the <e-calender-component> component. This function is called each time a
   * prop changes.
   */
  render() {
    return html`
    <eui-datepicker></eui-datepicker>`;
  }
}

/**
 * Register the component as e-calender-component.
 * Registration can be done at a later time and with a different name
 */
CalenderComponent.register();

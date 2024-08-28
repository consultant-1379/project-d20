/**
 * Component PanelComponent is defined as
 * `<e-panel-component>`
 *
 * Imperatively create component
 * @example
 * let component = new PanelComponent();
 *
 * Declaratively create component
 * @example
 * <e-panel-component></e-panel-component>
 *
 * @extends {LitComponent}
 */
import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './panelComponent.css';

/**
 * @property {Boolean} propOne - show active/inactive state.
 * @property {String} propTwo - shows the "Hello World" string.
 */
@definition('e-panel-component', {
  style,
  home: 'panel-component',
  props: {
    propOne: { attribute: true, type: Boolean },
    propTwo: { attribute: true, type: String, default: 'Hello World' },
  },
})
export default class PanelComponent extends LitComponent {
  /**
   * Render the <e-panel-component> component. This function is called each time a
   * prop changes.
   */


  render() {
    return html`
   <eui-layout-v0-multi-panel-tile tile-title="Build Stabilty Indicator">
  <div slot="content">
    <!-- Content for multi panel tile goes here -->
  </div>

  <eui-layout-v0-tile-panel
    id="filter"
    tile-title="Jobs"
    slot="left"
    icon-name="filter"
    width=400
  >
    <div slot="content">
    <div class="accordion-group" @click=${(event) => {this.handleAutoClose(event)}}>
      <eui-base-v0-accordion id="one" category-title="Job 1">
          <eui-base-v0-menu-item value="item-1" label="Build 234" @eui-menuItem:click="${(event) => console.log('')}"></eui-base-v0-menu-item>
      </eui-base-v0-accordion>
      <eui-base-v0-accordion id="two" category-title="Job 2"> 
          <eui-base-v0-menu-item value="item-1" label="Build 473"></eui-base-v0-menu-item>
      </eui-base-v0-accordion>
      <eui-base-v0-accordion id="three" category-title="Job 3"> 
          <eui-base-v0-menu-item value="item-1" label="Build 382"></eui-base-v0-menu-item>
      </eui-base-v0-accordion>
      <eui-base-v0-accordion id="four" category-title="Job 4"> 
          <eui-base-v0-menu-item value="item-1" label="Build 42"></eui-base-v0-menu-item>     
      </eui-base-v0-accordion>
    </div>
    </div>
  </eui-layout-v0-tile-panel>`;
  }
}


/**
 * Register the component as e-panel-component.
 * Registration can be done at a later time and with a different name
 */
PanelComponent.register();

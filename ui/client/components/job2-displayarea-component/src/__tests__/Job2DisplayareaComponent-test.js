/**
 * Integration tests for <e-job2-displayarea-component>
 */
import { expect } from 'chai';
import '../Job2DisplayareaComponent';
import {
  inShadow,
  injectHTMLElement,
  simulateEvent,
  nextTick
} from '../../../../../test/utils';

describe('Job2DisplayareaComponent Component Tests', () => {
    let container;
    let inject;
    before(() => {
      container = document.body.appendChild(document.createElement('div'));
      inject = injectHTMLElement.bind(null, container);
    });

    after(() => {
      document.body.removeChild(container);
    });

    describe('Basic component setup', () => {
      it('should create a new <e-job2-displayarea-component>', async () => {
        const customElement = await inject('<e-job2-displayarea-component></e-job2-displayarea-component>');
        // check shadow DOM
        const headingTag = inShadow(customElement, 'h1');
        expect(headingTag.textContent, '"Your component markup goes here" was not found').to.equal('Your component markup goes here');
      });
    });
});

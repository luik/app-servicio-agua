import { platformBrowserDynamic  } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { AppComponent, environment} from './app/';
import { AppModule } from './app/app.module';
import {disableDeprecatedForms, provideForms} from '@angular/forms';

if (environment.production) {
  enableProdMode();
}

//bootstrap(AppComponent, [APP_ROUTER_PROVIDER, disableDeprecatedForms(), provideForms()]);
platformBrowserDynamic().bootstrapModule(AppModule);

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
;
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { homecomponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ColorPickerModule } from 'ngx-color-picker';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {HotkeyModule} from 'angular2-hotkeys';
@NgModule({
  declarations: [
    AppComponent,
    homecomponent
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ColorPickerModule,
    HttpClientModule,
    HotkeyModule.forRoot()
    
    
  ],
  providers: [
    HttpClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { Component, effect, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ToastService} from "./service/toast.service";
import {fontAwesomeIcons} from "./shared/font-awesome-icons";
import {NavigationComponent} from "./layout/navigation/navigation.component";
import { HeaderComponent } from './layout/header/header.component';
import {FaIconLibrary, FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import{LibraryComponent} from "./layout/library/library.component";
import {PlayerComponent} from "./layout/player/player.component";
import { HttpClientModule } from '@angular/common/http';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule,PlayerComponent,LibraryComponent, NgbModule,NavigationComponent,HeaderComponent,FontAwesomeModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  title = 'spotify-clone-frontend';
  private faIconLibrary = inject(FaIconLibrary);
  
  toastService = inject(ToastService);


  ngOnInit(): void {
    this.initFontAwesome();
  }

  private initFontAwesome() {
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  
}

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
import {AuthService,AuthPopupState} from "./service/auth.service.";
import {NgbModal, NgbModalRef, NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {AuthPopupComponent} from "./layout/auth-popup/auth-popup.component";
@Component({
  selector: 'app-root',
  standalone: true,
  imports:  [RouterOutlet, FontAwesomeModule, NavigationComponent, LibraryComponent, HeaderComponent, NgbModule, PlayerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'spotify-clone-front';

  private faIconLibrary = inject(FaIconLibrary);

  toastService = inject(ToastService);

  private authService = inject(AuthService);

  private modalService = inject(NgbModal);

  private authModalRef: NgbModalRef | null = null;

  constructor() {
    effect(() => {
      this.openOrCloseAuthModal(this.authService.authPopupStateChange())
    }, {allowSignalWrites: true});
  }

  ngOnInit(): void {
    this.initFontAwesome();
  }

  private initFontAwesome() {
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  private openOrCloseAuthModal(state: AuthPopupState) {
    if(state === "OPEN") {
      this.openAuthPopup();
    } else if(this.authModalRef !== null && state === "CLOSE"
    && this.modalService.hasOpenModals()) {
      this.authModalRef.close();
    }
  }

  private openAuthPopup() {
    this.authModalRef = this.modalService.open(AuthPopupComponent, {
      ariaDescribedBy: 'authentication-modal',
      centered: true
    });

    this.authModalRef.dismissed.subscribe({
      next: () => {
        this.authService.openOrCloseAuthPopup("CLOSE");
      }
    });

    this.authModalRef.closed.subscribe({
      next: () => {
        this.authService.openOrCloseAuthPopup("CLOSE");
      }
    });
  }
}
import { Component } from '@angular/core';
import { WebSocketService } from '../services/web-socket.service';
import { AuthenticationService } from '../services/authenication-service/authentication.service';

@Component({
  selector: 'app-message-input',
  templateUrl: './message-input.component.html',
  styleUrls: ['./message-input.component.sass']
})
export class MessageInputComponent {
  message: string = '';

  constructor(
    private webSocketService: WebSocketService,
    private authService: AuthenticationService // Inject the AuthenticationService
  ) {}

  sendMessage() {
    if (this.authService.isLoggedIn()) {
      // User is authenticated, allow them to send messages
      if (this.message) {
        this.webSocketService.sendMessage(this.message);
        this.message = ''; // Clear the input field
      }
    } else {
      // User is not authenticated, display a message or redirect to login
      console.log('User is not authenticated. Please log in.');
    }
  }
}

import { Component } from '@angular/core';
import { WebSocketService } from '../services/web-socket.service';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.sass']
})
export class MessageListComponent {
  messages: string[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit() {
    // Subscribe to incoming messages
    this.webSocketService.receiveMessage().subscribe((message: string) => {
      this.messages.push(message);
    });
  }
}

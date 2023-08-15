import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private successMessage: string | undefined;
  private errorMessage: string | undefined;

  constructor() { }

  showSuccessMessage(message: string) {
    this.successMessage = message;
    setTimeout(() => (this.successMessage = undefined), 5000);
  }

  showErrorMessage(message: string) {
    this.errorMessage = message;
    setTimeout(() => (this.errorMessage = undefined), 5000); 
  }

  getSuccessMessage() {
    return this.successMessage;
  }

  getErrorMessage() {
    return this.errorMessage;
  }
}

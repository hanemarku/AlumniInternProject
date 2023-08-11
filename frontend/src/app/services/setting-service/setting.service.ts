import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export class Setting{
  key: string;
  value: string;
  category: string;

  constructor(key: string, value: string, category: string){
    this.key = key;
    this.value = value;
    this.category = category;
  }
}

@Injectable({
  providedIn: 'root'
})
export class SettingService {
  private baseUrl = 'http://localhost:8080/api/v1/settings';


  constructor(
    private http: HttpClient,
  ) { }

  getAllSettings(): Observable<Setting[]> {
    return this.http.get<Setting[]>(this.baseUrl);
  }

  saveMailServerSettings(mailServerSettings: Setting[]): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/save_mail_server`, mailServerSettings);
  }

  saveMailTemplateSettings(mailTemplateSettings: Setting[]): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/save_mail_templates`, mailTemplateSettings);
  }

  confirmAccount(token: string): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/confirm_account/${token}`);
  }


}

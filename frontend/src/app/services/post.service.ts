import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { Post } from '../post/post';
import { map } from 'rxjs/operators';
import { data } from 'jquery';
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(
    private http: HttpClient
  ) { }

  apiUrl = 'http//localhost:8080/api/v1/posts';

  getPost(postId: string): Observable<Post | undefined>{
    return this.http.get<Post>(`${this.apiUrl}/getPost/${postId}`);
  }

  getPostsByUser(author: any): Observable<Post[]>{
    return this.http.get<Post[]>(`${this.apiUrl}/user/${author}`);
  }

  createPost(post:Post): Observable<Post>{
    return this.http.post<Post>(`${this.apiUrl}/createPost`, post);
  }

  updatePost(postId: string, post: Post):Observable<Post>{
    return this.http.put<Post>(`${this.apiUrl}/update/${postId}`, post);
  }

  deletePost(postId:string):Observable<Post>{
    return this.http.delete<Post>(`${this.apiUrl}/delete/${postId}`);
  }
}

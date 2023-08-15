import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user-service/user-data.service';

export interface Post{
  postId: number;
  author: User;
  content: string;
  postCreation: Date;
  profilePicUrl: string;
}
@Injectable({
  providedIn: 'root'
})
export class PostService {
  private baseUrl = 'http://localhost8080/api/v1/posts';

  constructor(private http: HttpClient) { }

  getPostsByUser(userId: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.baseUrl}/user/${userId}`);
  }

  getPost(postId: number): Observable<Post> {
    return this.http.get<Post>(`${this.baseUrl}/getpost/${postId}`);
  }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(`${this.baseUrl}/cratePost`, post);
  }

  updatePost(postId: number, post: Post): Observable<Post> {
    return this.http.put<Post>(`${this.baseUrl}/update/${postId}`, post);
  }

  deletePost(postId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${postId}`);
  }

}

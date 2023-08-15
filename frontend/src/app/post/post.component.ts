import { Component, OnInit } from '@angular/core';
import { PostService, Post } from '../services/post.service';
import { User } from '../services/user-service/user-data.service';
import { get } from 'jquery';


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.sass']
})
export class PostComponent implements OnInit {
  newPost: Post = { postId: 0, author: getUserByPost , content: '', postCreation: new Date, profilePicUrl: '' };

  posts: Post[] = [];

  constructor(private postService: PostService) {}

  ngOnInit(): void {
  }

  loadPost(postId:number): void {
    this.postService.getPost(postId).subscribe(posts => {
      this.posts;
    });
  }

  createPost(post: Post): void {
    this.postService.createPost(post).subscribe(newPost => {
      this.posts.push(newPost);
    });
  }

  getPostByUser(author: number){
    this.postService.getPostsByUser(author).subscribe(posts => {
      console.log(posts);
    }
      );
    }

  updatePost(postId:number, post:Post){
    this.postService.updatePost(postId, post).subscribe(updatedPost => {
      // Handle the updated post here
      console.log('Updated post:', updatedPost);
  
      // Update the post in the component's posts array if needed
      const index = this.posts.findIndex(p => p.postId === postId);
      if (index !== -1) {
        this.posts[index] = updatedPost;
      }

  }
    )
}

  deletePost(postId:number){
    this.postService.deletePost(postId).subscribe(() => {
      // Handle successful deletion here
      console.log(`Post with ID ${postId} deleted`);
  
      // Remove the deleted post from the component's posts array if needed
      this.posts = this.posts.filter(post => post.postId !== postId);
    }
    )
  }
}
function getUserByPost(post:Post): User {
  return post.author;
}


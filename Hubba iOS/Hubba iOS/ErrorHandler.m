//
//  errorHandler.m
//  Hubba iOS
//
//  Created by Jackson on 11/26/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "ErrorHandler.h"

@implementation ErrorHandler

-(id) init{
  return self;
}

-(void)handle:(int)i{
  if( i == 1 ) [self serve1];
  else if ( i == 2 ) [self serve2];
  else if ( i == 3 ) [self serve3];
  else if ( i == 4 ) [self serve4];
  
  return;
}

-(void) serve1{
  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Error:"
                                                  message:@"Please enter a valid email address."
                                                 delegate:self
                                        cancelButtonTitle:@"Okay"
                                        otherButtonTitles: nil];
  [alert show];
}
-(void) serve2{
  self.alert = [[UIAlertView alloc] initWithTitle:@"Error:"
                                                  message:@"Please fill all forms to continue."
                                                 delegate:self
                                        cancelButtonTitle:@"Okay"
                                        otherButtonTitles: nil];
  [self.alert show];
}
-(void) serve3{
  self.alert = [[UIAlertView alloc] initWithTitle:@"Error:"
                                          message:@"Please make sure passwords match and are at least 4 characters long."
                                         delegate:self
                                cancelButtonTitle:@"Okay"
                                otherButtonTitles: nil];
  [self.alert show];
}
-(void) serve4{
  self.alert = [[UIAlertView alloc] initWithTitle:@"Error:"
                                          message:@"Please make sure username is at least 4 characters long."
                                         delegate:self
                                cancelButtonTitle:@"Okay"
                                otherButtonTitles: nil];
  [self.alert show];
}

@end

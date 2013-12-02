//
//  ErrorHandler.h
//  Hubba iOS
//
//  Created by Jackson on 11/26/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <BlocksKit/BlocksKit.h>

@interface ErrorHandler : NSObject <UIAlertViewDelegate>

@property (nonatomic, strong) UIAlertView *alert;

-(void)handle:(int)i;

@end

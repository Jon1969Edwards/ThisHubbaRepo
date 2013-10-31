//
//  ViewController.m
//  Hubba iOS
//
//  Created by Jackson on 10/29/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "loginPage.h"

@interface login ()

@end

@implementation login

- (void)viewDidLoad
{
    [super viewDidLoad];
  
  UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0,0,100,100)];
  [view setBackgroundColor:[UIColor yellowColor]];
  [self.view addSubview:view];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end

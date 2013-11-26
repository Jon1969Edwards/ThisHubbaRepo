//
//  LoginSignup.h
//  Hubba iOS
//
//  Created by Jackson on 11/26/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Constant.h"
#import "mapPage.h"

@interface LoginSignup : UIViewController{
}

@property (nonatomic, strong) IBOutlet UIView *signUpInfoView;
@property (nonatomic, strong) IBOutlet UIView *logInInfoView;
@property (nonatomic, strong) IBOutlet UIView *buttonBack;
@property (nonatomic, strong) IBOutlet UIButton *moveForward;
@property (nonatomic, strong) IBOutlet UIButton *moveBackward;
@property (nonatomic, strong) IBOutlet UITextField *loginEmail;
@property (nonatomic, strong) IBOutlet UITextField *loginPassword;
@property (nonatomic, strong) IBOutlet UISwitch *rememberInfo;

@property (nonatomic) BOOL typePage;

-(IBAction)back:(id)sender;
-(IBAction)next:(id)sender;

@end

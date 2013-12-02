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
#import "ErrorHandler.h"

@interface LoginSignup : UIViewController{
}
@property (nonatomic, strong) IBOutlet UIView *signUpInfoView;
@property (nonatomic, strong) IBOutlet UITextField *signupName;
@property (nonatomic, strong) IBOutlet UITextField *signupUsername;
@property (nonatomic, strong) IBOutlet UITextField *signupEmail;
@property (nonatomic, strong) IBOutlet UITextField *signupPass;
@property (nonatomic, strong) IBOutlet UITextField *signupPass2;

@property (nonatomic, strong) IBOutlet UIView *logInInfoView;
@property (nonatomic, strong) IBOutlet UITextField *loginEmail;
@property (nonatomic, strong) IBOutlet UITextField *loginPassword;

@property (nonatomic, strong) IBOutlet UISwitch *rememberInfo;
@property (nonatomic, strong) IBOutlet UIView *toggleInfoView;
@property (nonatomic, strong) IBOutlet UIButton *moveForward;
@property (nonatomic, strong) IBOutlet UIView *buttonBack;

@property (nonatomic, strong) IBOutlet UIButton *moveBackward;

@property (nonatomic) BOOL typePage, raised, editing;
@property (nonatomic) ErrorHandler *handler;

-(IBAction)back:(id)sender;
-(IBAction)next:(id)sender;

@end

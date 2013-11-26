//
//  LoginSignup.m
//  Hubba iOS
//
//  Created by Jackson on 11/26/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "LoginSignup.h"

@interface LoginSignup () <UITextFieldDelegate>

@end

@implementation LoginSignup

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
  if( self.typePage ) [self.moveForward setTitle:@"SIGN UP" forState:UIControlStateNormal];
  else{
    [self.logInInfoView setHidden:NO];
    [self.moveForward setTitle:@"LOG IN" forState:UIControlStateNormal];
  }
  
  self.loginEmail.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Email" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  self.loginPassword.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Password" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  
  CAGradientLayer *gradient = [CAGradientLayer layer];
  [gradient setFrame:CGRectMake(0, 0, 160, 40)];
  [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
  [self.buttonBack.layer insertSublayer:gradient atIndex:0];
  [self.buttonBack.layer setBorderColor: [UIColor darkGrayColor].CGColor];
  [self.buttonBack.layer setBorderWidth:1];
  
  [self.moveBackward.layer setBorderWidth:2];
  [self.moveBackward.layer setBorderColor:[UIColor whiteColor].CGColor];
  [self.moveBackward.layer setCornerRadius:7];
  
  NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
  if( [defaults objectForKey:@"email"] != NULL ) [self.loginEmail setText:[defaults objectForKey:@"email"]];
  if( [defaults objectForKey:@"pass"] != NULL ) [self.loginPassword setText:[defaults objectForKey:@"pass"]];
  
  [self.loginPassword.layer setBorderWidth:1];
  [self.loginPassword.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.loginPassword.layer setCornerRadius:5];
  [self.loginEmail.layer setBorderWidth:1];
  [self.loginEmail.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.loginEmail.layer setCornerRadius:5];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(bool)emailFormat:(NSString *)string{
  BOOL stricterFilter = YES; // Discussion http://blog.logichigh.com/2010/09/02/validating-an-e-mail-address/
  NSString *stricterFilterString = @"[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}";
  NSString *laxString = @".+@([A-Za-z0-9]+\\.)+[A-Za-z]{2}[A-Za-z]*";
  NSString *emailRegex = stricterFilter ? stricterFilterString : laxString;
  NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
  NSLog(@"%i",[emailTest evaluateWithObject:string]);
  return [emailTest evaluateWithObject:string];
}

-(IBAction)next:(id)sender{
  if( !self.typePage ){
    if( ![self emailFormat:self.loginEmail.text] || [self.loginPassword.text isEqualToString:@""]){
      UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Error:"
                                                      message:@"Please fill all forms to continue"
                                                     delegate:self
                                            cancelButtonTitle:@"Okay"
                                            otherButtonTitles: nil];
      [alert show];
    }
    else{
      NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
      if( self.rememberInfo.isOn ){
        [defaults setObject:self.loginEmail.text forKey:@"email"];
        [defaults setObject:self.loginPassword.text forKey:@"pass"];
      }
      else{
        [defaults setObject:NULL forKey:@"email"];
        [defaults setObject:NULL forKey:@"pass"];
      }
      [defaults synchronize];
      
      mapPage *newVC = [[mapPage alloc] init];
      [self.navigationController pushViewController:newVC animated:YES];
    }
  }
}

-(IBAction)back:(id)sender{
  [self.navigationController popViewControllerAnimated:YES];
}

-(bool)textFieldShouldReturn:(UITextField *)textField{
  if( textField.tag == 1 ) [[self.view viewWithTag:2] becomeFirstResponder];
  
  if( textField.tag == 2 ) [textField resignFirstResponder];
  return YES;
}
@end

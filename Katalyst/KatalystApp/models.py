from django.db import models
from django.contrib.auth.models import User

class Qualification(models.Model):
    institute = models.CharField(max_length=512,default=None, null=True, blank=True)
    degree = models.CharField(max_length=512,default=None, null=True, blank=True)
    start_date = models.IntegerField(max_length=5,default=None, null=True, blank=True)
    end_date = models.IntegerField(max_length=5,default=None, null=True, blank=True)

    def __unicode__(self):
        return self.institute + " " + self.degree


class Mentor(models.Model):
    first_name = models.CharField(max_length=512,default=None, null=True, blank=True)
    last_name = models.CharField(max_length=512,default=None, null=True, blank=True)
    email = models.CharField(max_length=512,default=None, null=True, blank=True)
    mobile = models.CharField(max_length=10,default=None, null=True, blank=True)
    GENDER_CHOICES = ((1, 'Male'), (2, 'Female'))
    gender = models.IntegerField(choices=GENDER_CHOICES)
    age = models.IntegerField(max_length=4, blank=True, null=True, default=None)
    location = models.CharField(max_length=512,default=None, null=True, blank=True)
    max_num_of_students = models.IntegerField(max_length=1)
    occupation = models.CharField(max_length=512,default=None, null=True, blank=True)
    qualifications = models.ManyToManyField(Qualification)
    gcm = models.CharField(max_length=512,default=None, null=True, blank=True)
    uuid = models.CharField(max_length=512,default=None, null=True, blank=True)
    django_user = models.OneToOneField(User)

    def __unicode__(self):
        return self.first_name+" "+self.last_name

class Mentee(models.Model):
    first_name = models.CharField(max_length=512,default=None, null=True, blank=True)
    last_name = models.CharField(max_length=512,default=None, null=True, blank=True)
    mobile = models.CharField(max_length=10,default=None, null=True, blank=True)
    email = models.CharField(max_length=512,default=None, null=True, blank=True)
    GENDER_CHOICES = ((1, 'Male'), (2, 'Female'))
    gender = models.IntegerField(choices=GENDER_CHOICES)
    age = models.IntegerField(max_length=4, blank=True, null=True, default=None)
    location = models.CharField(max_length=512,default=None, null=True, blank=True)
    gcm = models.CharField(max_length=512,default=None, null=True, blank=True)
    uuid = models.CharField(max_length=512,default=None, null=True, blank=True)
    assigned_mentor = models.ForeignKey(Mentor)
    django_user = models.OneToOneField(User)

    def __unicode__(self):
        return self.first_name+" "+self.last_name


class Review(models.Model):
    mentor = models.ForeignKey(Mentor)
    mentee = models.ForeignKey(Mentee)
    rating = models.IntegerField(max_length=3)
    text = models.CharField(max_length=1024)

    def __unicode__(self):
        return self.mentor.first_name + " to " + self.mentee.first_name

class Goals(models.Model):
    name = models.CharField(max_length=512,default=None, null=True, blank=True)
    description = models.CharField(max_length=1024,default=None, null=True, blank=True)

    def __unicode__(self):
        return self.name


class Appointment(models.Model):
    location = models.CharField(max_length=512,default=None, null=True, blank=True)
    mentor = models.ForeignKey(Mentor)
    mentee = models.ForeignKey(Mentee)
    datetime = models.CharField(max_length=512,default=None, null=True, blank=True)
    goals = models.ManyToManyField(Goals)

    def __unicode__(self):
        return self.mentor.first_name + " and " + self.mentee.first_name+"'s meeting"

class Feedback(models.Model):
    appointment = models.ForeignKey(Appointment)
    rating = models.IntegerField(max_length=3)
    text = models.CharField(max_length=1024)

    def __unicode__(self):
        return self.appointment.mentee.first_name + " to " + self.appointment.mentor.first_name

class AppointmentChat(models.Model):
    appointment = models.ForeignKey(Appointment)
    SENDER_CHOICES = ((1, 'Mentor'), (2, 'Mentee'))
    sender = models.IntegerField(choices=SENDER_CHOICES)
    text = models.CharField(max_length=512,default=None, null=True, blank=True)

    def __unicode__(self):
        return self.appointment.mentor.first_name + " and " + self.appointment.mentee.first_name + "'s chat"



class Reminders(models.Model):
    mentor = models.ForeignKey(Mentor)
    mentee = models.ForeignKey(Mentee)
    datetime = models.CharField(max_length=512,default=None, null=True, blank=True)
    for_appointment = models.ForeignKey(Appointment)

    def __unicode__(self):
        return "Reminder for " + self.mentor.first_name + " and " + self.mentee.first_name + "'s appointment"

class Unsent(models.Model):
    mobile = models.CharField(max_length=512)
    msg = models.CharField(max_length=150)

    def __unicode__(self):
        return "Unsent Message #"+str(self.id)
